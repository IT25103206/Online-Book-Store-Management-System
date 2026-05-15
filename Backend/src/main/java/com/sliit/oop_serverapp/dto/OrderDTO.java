package com.sliit.oop_serverapp.dto;

import lombok.Data;
import java.time.LocalDate;

/**
 * OOP Concept: Encapsulation (Data Transfer Object)
 * OrderDTO encapsulates order placement data for secure transfer.
 */
@Data
public class OrderDTO {
    private Integer id;
    private LocalDate date;
    private Integer userId;
    private Integer statusId;
}
