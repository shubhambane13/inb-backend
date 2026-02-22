package com.netbank.inb.service.impl;

import com.netbank.inb.dto.PageableResponse;
import com.netbank.inb.dto.TransactionDto;
import com.netbank.inb.dto.TransactionFilterRequest;
import com.netbank.inb.dto.UserDto;
import com.netbank.inb.entity.Transaction;
import com.netbank.inb.repository.TransactionRepository;
import com.netbank.inb.service.TransactionService;
import com.netbank.inb.util.Util;
import jakarta.persistence.criteria.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public PageableResponse<TransactionDto> getFilteredTransactions(TransactionFilterRequest request) {
        // 1. Build the dynamic query
        Specification<Transaction> spec = buildFilterSpec(request);

        // 2. Configure Sorting (Always newest first)
        Sort sort = Sort.by(Sort.Direction.DESC, "transactionDate");
        Pageable pageable;

        // 3. Handle Pagination vs Mini Statement logic
        pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

        // 4. Execute single dynamic query!
        return Util.getPageableResponse(transactionRepository.findAll(spec, pageable), TransactionDto.class);
    }

    public static Specification<Transaction> buildFilterSpec(TransactionFilterRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Create LEFT JOINs for both fromAccount and toAccount
            Join<Object, Object> fromAccountJoin = root.join("fromAccount", JoinType.LEFT);
            Join<Object, Object> toAccountJoin = root.join("toAccount", JoinType.LEFT);

            // 2. MANDATORY: The transaction must involve at least one account owned by the customer
            Predicate fromCustomerMatch = criteriaBuilder.equal(fromAccountJoin.get("customer").get("id"), request.getUserId());
            Predicate toCustomerMatch = criteriaBuilder.equal(toAccountJoin.get("customer").get("id"), request.getUserId());
            predicates.add(criteriaBuilder.or(fromCustomerMatch, toCustomerMatch));

            // 3. OPTIONAL: Filter by specific Account ID
            if (request.getAccountId() != null) {
                Predicate fromAccountMatch = criteriaBuilder.equal(fromAccountJoin.get("id"), request.getAccountId());
                Predicate toAccountMatch = criteriaBuilder.equal(toAccountJoin.get("id"), request.getAccountId());
                predicates.add(criteriaBuilder.or(fromAccountMatch, toAccountMatch));
            }

            // 4. OPTIONAL: Filter by Date Range
            if (request.getFromDate() != null && request.getToDate() != null) {
                predicates.add(criteriaBuilder.between(root.get("transactionDate"), request.getFromDate(), request.getToDate()));
            } else if (request.getFromDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("transactionDate"), request.getFromDate()));
            } else if (request.getToDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("transactionDate"), request.getToDate()));
            }

            // Combine all predicates with AND
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
