package ru.torchikov.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Torchikov Sergei on 26.07.2017.
 *
 */
@Configuration
@Import({MessageSystemConfig.class})
public class ApplicationConfig {
}
