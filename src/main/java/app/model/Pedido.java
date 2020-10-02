package app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PEDIDO")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @JoinColumn(name = "ID_CLIENTE")
    @ManyToOne
    private Cliente cliente;

    @Column(name = "DATA_PEDIDO")
    private LocalDate data_pedido;

    @Column(name = "TOTAL", precision = 20, scale = 2)
    private BigDecimal total;

    @Column(name = "NUMERO_CONTROLE")
    private Integer numero_controle;

    @OneToMany(mappedBy = "pedido")
    private List<Item_pedido> itens;

}