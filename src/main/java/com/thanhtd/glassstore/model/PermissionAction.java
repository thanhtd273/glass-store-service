package com.thanhtd.glassstore.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "permission_action")
public class PermissionAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "permission_id")
    private Long permissionId;

    @Column(name = "action_id")
    private Long actionId;
}
