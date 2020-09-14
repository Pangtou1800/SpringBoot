package pt.joja.service;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;


@Component
@DubboService
public class TicketServiceImpl implements TicketService {
    @Override
    public String getTicket() {
        return "<Alice in Wonderland>";
    }
}
