package com.netbank.inb.repository;

import com.netbank.inb.entity.DropdownElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DropdownRepository extends JpaRepository<DropdownElement, Long> {

    // We only need to fetch by elementId. The rest we do in memory.
    List<DropdownElement> findByElementId(String elementId);
}