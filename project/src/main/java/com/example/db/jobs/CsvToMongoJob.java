package com.example.db.jobs;
 
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.example.db.model.User;
import com.example.db.model.Movie;
import com.example.db.model.Rating;
 
@EnableBatchProcessing
@Configuration
public class CsvToMongoJob {
 
  @Autowired
  private JobBuilderFactory jobBuilderFactory;
  @Autowired
  private StepBuilderFactory stepBuilderFactory;
 
  @Autowired
  private MongoTemplate mongoTemplate;
 
  @Bean
  public Job readCSVFile() {
    return jobBuilderFactory.get("readCSVFile").incrementer(new RunIdIncrementer())
      .start(step1()).next(step2()).next(step3()).build();
      
  }
 
  @Bean
  public Step step1() {
    return stepBuilderFactory.get("step1").<User, User>chunk(10).reader(userReader())
        .writer(userWriter()).build();
  }
 
  @Bean
  public FlatFileItemReader<User> userReader() {
    FlatFileItemReader<User> reader = new FlatFileItemReader<>();
    reader.setResource(new ClassPathResource("users.dat"));
    reader.setLineMapper(new DefaultLineMapper<User>() {{
      setLineTokenizer(new DelimitedLineTokenizer() {{
        setDelimiter("::");
        setNames(new String[]{"id", "gender", "age", "occupation", "zipCode"});
      }});
      setFieldSetMapper(new BeanWrapperFieldSetMapper<User>() {{
        setTargetType(User.class);
      }});
    }});
    return reader;
  }
 
  @Bean
  public MongoItemWriter<User> userWriter() {
    MongoItemWriter<User> writer = new MongoItemWriter<User>();
    writer.setTemplate(mongoTemplate);
    writer.setCollection("user");
    return writer;
  }

  @Bean
  public Step step2() {
    return stepBuilderFactory.get("step2").<Movie, Movie>chunk(10).reader(movieReader())
        .writer(movieWriter()).build();
  }
 
  @Bean
  public FlatFileItemReader<Movie> movieReader() {
    FlatFileItemReader<Movie> reader = new FlatFileItemReader<Movie>();
    reader.setResource(new ClassPathResource("movies.dat"));
    reader.setLineMapper(new DefaultLineMapper<Movie>() {{
      setLineTokenizer(new DelimitedLineTokenizer() {{
        setDelimiter("::");
        setNames(new String[]{"id", "title", "genres"});
      }});
      setFieldSetMapper(new BeanWrapperFieldSetMapper<Movie>() {{
        setTargetType(Movie.class);
      }});
    }});
    return reader;
  }
 
  @Bean
  public MongoItemWriter<Movie> movieWriter() {
    MongoItemWriter<Movie> writer = new MongoItemWriter<Movie>();
    writer.setTemplate(mongoTemplate);
    writer.setCollection("movie");
    return writer;
  }

  @Bean
  public Step step3() {
      return stepBuilderFactory.get("step3").<Rating, Rating>chunk(10).reader(ratingReader())
              .writer(ratingWriter()).build();
  }

  @Bean
  public FlatFileItemReader<Rating> ratingReader() {
      FlatFileItemReader<Rating> reader = new FlatFileItemReader<>();
      reader.setResource(new ClassPathResource("ratings.dat"));
      reader.setLineMapper(new DefaultLineMapper<Rating>() {{
          setLineTokenizer(new DelimitedLineTokenizer() {{
              setDelimiter("::");
              setNames(new String[]{"userId", "movieId", "rating", "timestamp"});
          }});
          setFieldSetMapper(new BeanWrapperFieldSetMapper<Rating>() {{
              setTargetType(Rating.class);
          }});
      }});
      return reader;
  }

  @Bean
  public MongoItemWriter<Rating> ratingWriter() {
      MongoItemWriter<Rating> writer = new MongoItemWriter<>();
      writer.setTemplate(mongoTemplate);
      writer.setCollection("rating");
      return writer;
  }
}