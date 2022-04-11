package pe.upload.file.springbatch.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Transaccion implements Serializable {

    private String nroOperacion;

    private BigDecimal importe;

    private LocalDateTime fechaProceso;
}
