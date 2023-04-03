package entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "laboratorios")
@NoArgsConstructor
@AllArgsConstructor
@Data


public class LaboratorioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (unique = true, nullable = false)
    private Long id_lab;


    private float grasas;
    private float solidos;
    private String  id_acopio;


}
