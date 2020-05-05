package mieker.myOwnHibernate;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "persons")
public class Person implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "master_id")
	private Set<Pup> pups;
	
	public Person() {
		
	}
	
	public Person(String name) {
		super();
		this.name = name;
	}
		
	public Set<Pup> getPups() {
		return pups;
	}

	public void setPups(Set<Pup> pups) {
		this.pups = pups;
	}
	
	public void addPup(Pup newPup) {
		pups.add(newPup);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "ID: " + this.getId().toString() + ", name: " + this.getName();
	}

}
