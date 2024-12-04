package congestion.calculator.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rate")
public class Rate {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne
	@JoinColumn(name = "city_id", nullable = false)
	private City city;

	@Column(name = "start_date")
	public Date startDate;

	@Column(name = "end_date")
	public Date endDate;

	@Column(name = "tax")
	private double tax;

	public Rate() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

}
