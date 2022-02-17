package com.github.Elmicass.SFJTeam_Casotto.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "Equipment")
@NoArgsConstructor
public class Equipment implements Serializable {

    public enum EquipmentType {
        Indoor,
        Outdoor;
    }

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	private Integer id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "Type")
    private EquipmentType type;

    @ManyToMany(mappedBy = "equipments")
    @Column(name = "Activities")
    @OrderBy("Timeslot ASC, ID ASC")
    private SortedSet<Activity> scheduledActivities;

    public Equipment(String name, String description, String type) throws IllegalArgumentException {
        this.scheduledActivities = new TreeSet<Activity>();
        setName(name);
        setDescription(description);
        setType(type);
    }

    public Integer getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (Objects.requireNonNull(name, "Name value is null.").isBlank())
            throw new IllegalArgumentException("The equipment name is empty.");
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws IllegalArgumentException {
        if (Objects.requireNonNull(description, "Description value is null.").isBlank())
            throw new IllegalArgumentException("The equipment description is empty.");
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

    public boolean isFree(TimeSlot timeSlot) {
        for (Activity activity : scheduledActivities) {
            if (activity.getEquipments().contains(this) && activity.getTimeSlot().overlapsWith(Objects.requireNonNull(timeSlot,"The given timeslot is null.")))
                return false;
        }
        return true;
    }


    public SortedSet<Activity> getActivity() {
        return this.scheduledActivities;
    }

    public void setActivity(SortedSet<Activity> act) {
        Objects.requireNonNull(act, "The role privileges set is null.");
        this.scheduledActivities = act;
    }

    public boolean addActivity(Activity act) {
        if (scheduledActivities.contains(act))
            throw new IllegalStateException("This equipment is already scheduled to be used by the given activity.");
        if (isFree(act.getTimeSlot()))
            return scheduledActivities.add(act);
        else
            throw new IllegalStateException(
                    "This equipment is already scheduled to be used by an activity in the same time slot of the given activity.");
    }

    public boolean removeActivity(Activity act) {
        if (!(scheduledActivities.contains(Objects.requireNonNull(act, "The given activity is null."))))
            throw new IllegalArgumentException(
                    "The activity you are trying to remove is not scheduled for this equipment.");
        this.scheduledActivities.removeIf(a -> Objects.equals(a, act));
        return true;
    }

    


    





    
}
