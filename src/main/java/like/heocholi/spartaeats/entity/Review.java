package like.heocholi.spartaeats.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "reviews")
@Getter
public class Review extends Timestamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id", nullable = false)
	private Store store;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	private String contents;

	private int likeCount;

	public void updateLike(boolean result) {
		this.likeCount = result ? this.likeCount + 1 : this.likeCount - 1;
	}

	// 새로운 setLike 메서드 추가
	public void setLike(int likeCount) {
		this.likeCount = likeCount;
	}
}