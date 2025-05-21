package com.example.turistickivodic;

import com.example.turistickivodic.filters.AuthFilter;
//import com.example.turistickivodic.filters.AuthServletFilter;
import com.example.turistickivodic.filters.AuthServletFilter;
import com.example.turistickivodic.repositories.activity.ActivityRepository;
import com.example.turistickivodic.repositories.activity.MySqlActivityRepository;
import com.example.turistickivodic.repositories.article.ArticleRepository;
import com.example.turistickivodic.repositories.article.MySqlArticleRepository;
import com.example.turistickivodic.repositories.articleactivity.ArticleActivityRepository;
import com.example.turistickivodic.repositories.articleactivity.MySqlArticleActivityRepository;
import com.example.turistickivodic.repositories.comment.CommentRepository;
import com.example.turistickivodic.repositories.comment.MySqlCommentRepository;
import com.example.turistickivodic.repositories.destination.DestinationRepository;
import com.example.turistickivodic.repositories.destination.MySqlDestinationRepository;
import com.example.turistickivodic.repositories.user.MySqlUserRepository;
import com.example.turistickivodic.repositories.user.UserRepository;
import com.example.turistickivodic.services.*;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {

    public HelloApplication() {
        // Ukljucujemo validaciju
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        // Definisemo implementacije u dependency container-u
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(MySqlUserRepository.class).to(UserRepository.class).in(Singleton.class);
                this.bind(MySqlDestinationRepository.class).to(DestinationRepository.class).in(Singleton.class);
                this.bind(MySqlActivityRepository.class).to(ActivityRepository.class).in(Singleton.class);
                this.bind(MySqlArticleRepository.class).to(ArticleRepository.class).in(Singleton.class);
                this.bind(MySqlArticleActivityRepository.class).to(ArticleActivityRepository.class).in(Singleton.class);
                this.bind(MySqlCommentRepository.class).to(CommentRepository.class).in(Singleton.class);

                this.bindAsContract(UserService.class);
                this.bindAsContract(DestinationService.class);
                this.bindAsContract(ActivityService.class);
                this.bindAsContract(ArticleService.class);
                this.bindAsContract(ArticleActivityService.class);
                this.bindAsContract(CommentService.class);

            }
        };
        register(binder);

        // Ucitavamo resurse
        packages("com.example.turistickivodic.resources");
        register(AuthFilter.class);
        //register(AuthServletFilter.class);
    }
}