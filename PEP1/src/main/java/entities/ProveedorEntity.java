package entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Entity
@Table(name = "proveedores")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class ProveedorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (unique = true, nullable = false)
    private Long codigo;


    private String nombre;
    private String categoria;
    private String retencion;

}
