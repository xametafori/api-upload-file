package pe.upload.file.springbatch.processor;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import pe.upload.file.springbatch.model.Movimiento;
import pe.upload.file.springbatch.model.MovimientoOut;

import java.time.LocalDateTime;

public class MovimientoProcessor implements ItemProcessor<Movimiento, MovimientoOut> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovimientoProcessor.class);

    @Autowired
    private ModelMapper mapper;

    @Override
    public MovimientoOut process(Movimiento item) throws Exception {
        LOGGER.info("Procesando el movimiento {}", item);
        item.setFechaProceso(LocalDateTime.now());
       return mapper.map(item, MovimientoOut.class);

    }

}

