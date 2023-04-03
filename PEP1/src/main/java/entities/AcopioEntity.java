package entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.ManyToAny;

import java.util.Date;

@Entity
@Table(name = "acopios")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class AcopioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (unique = true, nullable = false)
    private Long id_acopio;



    private String nombre;
    private Date fecha;

    @ManyToOne
    public ProveedorEntity prov;
}
