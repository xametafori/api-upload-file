package pe.upload.file.springbatch.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name="BCO_MOVIMIENTOS",schema = "EFINAN")
public class MovimientoBco implements Serializable {


    private static final long serialVersionUID = 1832077028846320985L;

    @Id
    @GeneratedValue(generator = "seqGeneradorMov", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "seqGeneradorMov", sequenceName = "EFINAN.SEQ_BCO_MOVIMIENTOS", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name="nro_operacion")
    private String nroOperacion;

    @Column(name="importe")
    private BigDecimal importe;

    @Column(name="fecha_proceso")
    private LocalDateTime fechaProceso;



}
