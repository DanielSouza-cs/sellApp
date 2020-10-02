package app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ITEM_PEDIDO")
public class Item_pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @JoinColumn(name = "ID_PRODUTO")
    @ManyToOne
    private Produto produto;

    @JoinColumn(name = "ID_PEDIDO")
    @ManyToOne
    private Pedido pedido;

    @Column(name = "QUANTIDADE")
    private Integer quantidade;
}
