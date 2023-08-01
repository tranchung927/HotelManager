package vn.edu.aptech.hotelmanager.repo.entity;

import lombok.Data;
import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import javax.persistence.*;

@Data
@Entity
@Table(name = "city")
public class CityEntity implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "country_id")
    private int countryId;

    @Column
    private String name;

    @Column
    private String code;

    @Column(name = "zip_code")
    private String zipCode;
}
