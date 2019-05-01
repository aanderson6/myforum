package com.myforum.subforum.entities;

//imports
import javax.persistence.*;

@Entity
@Table(name = "subforum_settings")
public class SubforumSettingsEntity {

    public SubforumSettingsEntity() {   }

    public SubforumSettingsEntity(SubforumEntity subforum, String rules) {
      this.subforum = subforum;
      this.rules = rules;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "subforum_id", nullable = false)
    private SubforumEntity subforum;

    @Column(name = "rules", nullable = false)
    private String rules;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SubforumEntity getSubforum() {
        return subforum;
    }

    public void setSubforum(SubforumEntity subforum) {
        this.subforum = subforum;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }
}
