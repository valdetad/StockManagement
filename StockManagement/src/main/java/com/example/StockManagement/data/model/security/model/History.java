//package com.example.StockManagement.data.model.security.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDate;
//
//@EqualsAndHashCode(callSuper = true)
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class History extends Audit {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "start_date", nullable = false)
//    private LocalDate startDate;
//
//    @Column(name = "end_date")
//    private LocalDate endDate;
//
//    @Column(name = "title", nullable = false)
//    private String title;
//
//    @Lob
//    @Column(name = "description")
//    private String description;
//
//    // Work or School for specific fields
//    @Column(name = "institution_name")
//    private String institutionName;
//
//    @Column(name = "role_or_degree")
//    private String roleOrDegree;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "history_type", nullable = false)
//    private EHistoryType historyType;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//}
//
