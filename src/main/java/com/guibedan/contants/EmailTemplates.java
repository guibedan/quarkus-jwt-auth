package com.guibedan.contants;

public class EmailTemplates {

    public static String resetPasswordEmail(String token) {
        String resetUrl = "http://localhost/reset-password?token=" + token;

        return """
            <!DOCTYPE html>
            <html lang="pt-BR">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f4f4f7;
                        margin: 0;
                        padding: 0;
                        color: #333333;
                    }
                    .container {
                        max-width: 600px;
                        margin: 40px auto;
                        background: #ffffff;
                        border-radius: 8px;
                        padding: 20px;
                        box-shadow: 0 2px 6px rgba(0,0,0,0.1);
                    }
                    h2 {
                        color: #2c3e50;
                    }
                    p {
                        line-height: 1.6;
                    }
                    .button {
                        display: inline-block;
                        padding: 12px 20px;
                        margin-top: 20px;
                        background-color: #007BFF;
                        color: #ffffff !important;
                        text-decoration: none;
                        border-radius: 6px;
                        font-weight: bold;
                    }
                    .footer {
                        margin-top: 30px;
                        font-size: 12px;
                        color: #999999;
                        text-align: center;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h2>Recuperação de senha</h2>
                    <p>Recebemos uma solicitação para redefinir a senha da sua conta.</p>
                    <p>Para continuar, clique no botão abaixo ou copie e cole o link no seu navegador:</p>
                    <a href="%s" class="button">Redefinir senha</a>
                    <p>Ou acesse diretamente: <br><a href="%s">%s</a></p>
                    <div class="footer">
                        <p>Você tem 1 hora para recuperar sua senha!</p>
                    </div>
                    <div class="footer">
                        <p>Se você não solicitou essa alteração, ignore este e-mail.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(resetUrl, resetUrl, resetUrl);
    }

}
