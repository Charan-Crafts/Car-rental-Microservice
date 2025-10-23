import com.growandshine.gateway.RouteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!routeValidator.isSecured(exchange)) {
                return chain.filter(exchange); // open route
            }

            String token = exchange.getRequest()
                    .getHeaders()
                    .getFirst("Authorization");

            if (token == null || !token.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            token = token.substring(7);



            return chain.filter(exchange);
        };
    }

    public AuthenticationFilter(){
        super(Config.class);
    }

    public static class Config{

    }
}
