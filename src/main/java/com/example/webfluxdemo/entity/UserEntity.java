package com.example.webfluxdemo.entity;

import com.example.webfluxdemo.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
@Accessors(chain = true)
public class UserEntity {

    @Id
    private Long id;
    private String username;
    private String password;
    private Role role;
    @Column("firstname")
    private String firstName;
    @Column("lastname")
    private String lastName;
    private boolean enable;
    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("updated_at")
    private LocalDateTime updatedAt;

    @ToString.Include(name = "password")
    private String maskPassword(){
        return "********";
    }

}
