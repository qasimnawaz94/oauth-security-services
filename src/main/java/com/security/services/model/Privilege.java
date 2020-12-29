package com.security.services.model;

import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "privileges")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Privilege extends BaseEntity {

    private String name;

    @Column(name = "page_url")
    private String pageUrl;

    private String endpoint;

    @ManyToMany(mappedBy = "privileges", fetch = FetchType.LAZY)
    private Collection<Role> roles;

    @ManyToMany(mappedBy = "privilege", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Collection<RolePrivilege> rolesPrivileges;

}
