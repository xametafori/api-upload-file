package pe.upload.file.springbatch.config;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import pe.upload.file.springbatch.listener.JobLoteMovimientosListener;
import pe.upload.file.springbatch.model.Movimiento;
import pe.upload.file.springbatch.model.MovimientoOut;
import pe.upload.file.springbatch.processor.MovimientoProcessor;
import pe.upload.file.springbatch.writer.MovimientoWriter;


@Configuration
@EnableBatchProcessing
public class JobLoteMovimientosConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    JobRepository jobRepository;

    @Value("${file.input}")
    private String fileInput;

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);// Número de hilos centrales
        executor.setMaxPoolSize(20);// Especifique el número máximo de hilos.
        executor.setQueueCapacity(200);// el número más grande en la cola
        executor.setKeepAliveSeconds(60);// Tiempo máximo de supervivencia después del hilo
        executor.setThreadNamePrefix("zhangxueliang-taskExecutor-");// prefijo de nombre de hilo
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
    @Bean
    public JobLauncher jobLoteMovimientosLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setTaskExecutor(taskExecutor());
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }


    @Bean
    public Job procesarLoteMovimientosJobOne(JobLoteMovimientosListener listener, Step stepOne) {
        return jobBuilderFactory.get("procesarLoteMovimientosJobOne")
                .listener(listener)
                .flow(stepOne())
                .end()
                .build();
    }

    @Bean
    public Job procesarLoteMovimientosJobTwo(JobLoteMovimientosListener listener, Step stepOne) {
        return jobBuilderFactory.get("procesarLoteMovimientosJobTwo")
                .listener(listener)
                .flow(stepOne())
                .end()
                .build();
    }

    @Bean
    public Step stepOne() {
        return stepBuilderFactory.get("stepOne")
                .<Movimiento, MovimientoOut> chunk(1)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public FlatFileItemReader<Movimiento> reader() {
        return new FlatFileItemReaderBuilder<Movimiento>()
                .name("movimientoItemReader")
                .resource(new ClassPathResource(fileInput))
                .delimited()
                .names(new String[] { "nroOperacion", "importe"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Movimiento>() {{
                    setTargetType(Movimiento.class);
                }})
                .build();
    }


    @Bean
    public MovimientoProcessor processor() {
        return new MovimientoProcessor();
    }

    @Bean
    public MovimientoWriter writer() {
        return new MovimientoWriter();
    }
}

