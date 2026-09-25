package io.github.ramon.ReadFlow.business.service.email;

import io.github.ramon.ReadFlow.infrastructure.entity.token.TokenEmailConfirmacao;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void enviarEmailConfirmacao(TokenEmailConfirmacao emailConfirmacao){

        String linkConfirmacao = "http://localhost:4200/confirmar-email?token=";

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(emailConfirmacao.getUsuario().getEmail());
        message.setSubject("Confirme seu e-mail - ReadFlow");
        message.setText(linkConfirmacao + emailConfirmacao.getToken());

        javaMailSender.send(message);
    }
}
