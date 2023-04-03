package entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Entity
@Table(name = "pagos")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class PagosEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (unique = true, nullable = false)
    private  Long id_pago;


    private  Integer total_acopios;
    private  Integer total ;
    private  String codigo_proveedor ;


    @ManyToOne
    private ProveedorEntity proveedor;


}
