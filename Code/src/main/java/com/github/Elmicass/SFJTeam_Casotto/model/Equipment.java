package com.github.Elmicass.SFJTeam_Casotto.model;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Equipment")
public class Equipment {

    public enum EquipmentType {
        Indoor,
        Outdoor;
    }

    protected static final AtomicInteger count = new AtomicInteger(0);

	@Id
	@Column(name = "ID")
	private final String ID;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private EquipmentType type;

    public Equipment(String name, String description, String type) throws IllegalArgumentException {
        this.ID = String.valueOf(count.getAndIncrement());
        setName(name);
        setDescription(description);
        setType(type);
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (Objects.requireNonNull(description, "Name value is null").isBlank())
			throw new IllegalArgumentException("The equipment name is empty");
		this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws IllegalArgumentException {
		if (Objects.requireNonNull(description, "Description value is null").isBlank())
			throw new IllegalArgumentException("The equipment description is empty");
		this.description = description;
	}

    public EquipmentType getType() {
        return type;
    }

    public void setType(String typeStringName) throws IllegalArgumentException {
        EquipmentType type = EquipmentType.valueOf(typeStringName);
        switch (type) {
            case Indoor:
                this.type = EquipmentType.Indoor;
                break;
            case Outdoor:
                this.type = EquipmentType.Outdoor;
                break;
            default:
                throw new IllegalArgumentException(
                        "The equipment type you are trying to create is not one of: " + EquipmentType.values() + ".");
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Equipment other = (Equipment) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    


    





    
}
