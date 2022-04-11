package pe.upload.file.springbatch.writer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.upload.file.springbatch.model.MovimientoBco;
import pe.upload.file.springbatch.model.MovimientoOut;
import pe.upload.file.springbatch.repository.MovimientoBcoRepository;
import org.modelmapper.ModelMapper;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MovimientoWriter implements ItemWriter<MovimientoOut> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovimientoWriter.class);


    @Autowired
    MovimientoBcoRepository movimientoBcoRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public void write(List<? extends MovimientoOut> items) throws Exception {
        LOGGER.info("Escribiendo los movimientos {}", items);

        items.forEach(x ->
                {
                   Optional<MovimientoBco> oMovimientoBco= movimientoBcoRepository.findByNroOperacion(x.getNroOperacion());
                   if(!oMovimientoBco.isPresent()){
                       movimientoBcoRepository.save(mapper.map(x,MovimientoBco.class));
                   }
                });
    }
}
