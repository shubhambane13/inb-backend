package com.netbank.inb.cache;

import com.netbank.inb.entity.DropdownElement;
import com.netbank.inb.repository.DropdownRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DropdownCacheManager {

    @Autowired
    private DropdownRepository dropdownRepository;

    // Thread-safe map to hold the cached data
    private Map<String, List<DropdownElement>> dropdownCache = new ConcurrentHashMap<>();

    /**
     * @PostConstruct tells Spring to run this method immediately after
     * this Bean is created and before the application starts accepting requests.
     */
    @PostConstruct
    public void loadCacheOnStartup() {
        log.info("Starting to load all dropdown elements into memory...");

        // 1. Fetch EVERYTHING from the database
        List<DropdownElement> allElements = dropdownRepository.findAll();

        // 2. Group them by elementId for instant lookups later
        dropdownCache = allElements.stream()
                .collect(Collectors.groupingBy(DropdownElement::getElementId));

        log.info("Successfully loaded {} dropdown elements into cache.", allElements.size());
    }

    /**
     * Method to fetch from the in-memory map safely
     */
    public List<DropdownElement> getListByElementId(String elementId) {
        // Return the list if it exists, otherwise return an empty list
        return dropdownCache.getOrDefault(elementId, Collections.emptyList());
    }

    /**
     * Optional: Call this via an Admin API if you add new dropdowns
     * to the database and want to refresh without restarting the server.
     */
    public void refreshCache() {
        loadCacheOnStartup();
    }
}