package like.heocholi.spartaeats.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import like.heocholi.spartaeats.constants.UserRole;

@Entity
@Table(name = "users")
public class User extends Timestamped{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String userId;
	
	private String name;
	
	private String password;
	
	private String address;
	
	private String refreshToken;
	
	@Enumerated(value = EnumType.STRING)
	private UserRole role;
	
	@OneToOne(mappedBy = "manager", fetch = FetchType.LAZY)
	private Store store;
}
