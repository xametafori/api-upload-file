package pe.upload.file.springbatch.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("v1/jobs")
@Api(value = "BatchRest", produces = "application/json", tags = { "Controlador Batch" })
public class BatchRest {

    @Autowired
    JobLauncher jobLoteMovimientosLauncher;

    @Autowired
    Job procesarLoteMovimientosJobOne;

    @Autowired
    Job procesarLoteMovimientosJobTwo;

    @ApiOperation(value = "Cargar movimiento del banco")
    @PostMapping("/execute")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Movimientos cargados!"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Error en el servidor") })
    public ResponseEntity<String> execute() throws Exception {

        ejecutar();
        return new ResponseEntity<>( "OK",HttpStatus.OK);
    }
   private void ejecutar()throws Exception {

       jobLoteMovimientosLauncher
               .run(procesarLoteMovimientosJobOne, new JobParametersBuilder()
                       .addLong("idInicio", System.nanoTime())
                       .toJobParameters());

       jobLoteMovimientosLauncher
               .run(procesarLoteMovimientosJobTwo, new JobParametersBuilder()
                       .addLong("idInicio", System.nanoTime())
                       .toJobParameters());

   }
}
