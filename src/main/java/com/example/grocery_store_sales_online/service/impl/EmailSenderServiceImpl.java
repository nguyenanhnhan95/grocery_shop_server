package com.example.grocery_store_sales_online.service.impl;

import com.example.grocery_store_sales_online.service.IEmailSenderService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderServiceImpl implements IEmailSenderService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${app.cors.domainClient}")
    private String domainClient;
    @Override
    public void notifyRegisterSuccess(String toEmail)  {
        try {
            log.info("EmailSenderService:sendEmail execution started.");
            //        SimpleMailMessage message = new SimpleMailMessage();
//        StringBuilder result = new StringBuilder();
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            String mailSubject = "[SMART-SKY] - Thông báo xác nhận tài khoản khách hàng";
            String mailContent = "<div style=\"margin: 0\">\n" +
                    "    <table style=\"border-spacing:0;border-collapse:collapse;height:100%!important;width:100%!important\">\n" +
                    "        <tbody>\n" +
                    "        <tr>\n" +
                    "            <td style=\"font-family:-apple-system,BlinkMacSystemFont,'Segoe UI','Roboto','Oxygen','Ubuntu','Cantarell','Fira Sans','Droid Sans','Helvetica Neue',sans-serif;text-align: center\">\n" +
                    "                <table style=\"border-spacing:0;border-collapse:collapse;width:100%;margin:40px 0 20px\">\n" +
                    "                    <tbody>\n" +
                    "                    <tr>\n" +
                    "                        <td style=\"font-family:-apple-system,BlinkMacSystemFont,'Segoe UI','Roboto','Oxygen','Ubuntu','Cantarell','Fira Sans','Droid Sans','Helvetica Neue',sans-serif\">\n" +
                    "                            <img src=\"https://file-upload-public-anhnhan.s3.ap-southeast-1.amazonaws.com/logo_sky_title.png\"\n" +
                    "                                 alt=\"\" width=\"180px\"/>\n" +
                    "                        </td>\n" +
                    "                    </tr>\n" +
                    "                    </tbody>\n" +
                    "                </table>\n" +
                    "                <table style=\"border-spacing:0;border-collapse:collapse;width:100%\">\n" +
                    "                    <tbody>\n" +
                    "                    <tr>\n" +
                    "                        <td style=\"font-family:-apple-system,BlinkMacSystemFont,'Segoe UI','Roboto','Oxygen','Ubuntu','Cantarell','Fira Sans','Droid Sans','Helvetica Neue',sans-serif;padding-bottom: 40px;display: flex;justify-content: center\">\n" +
                    "                            <table style=\"border-spacing:0;border-collapse:collapse;width:560px;text-align:left;margin:0 auto\">\n" +
                    "                                <tbody>\n" +
                    "                                <tr>\n" +
                    "                                    <td style=\"font-family:-apple-system,BlinkMacSystemFont,'Segoe UI','Roboto','Oxygen','Ubuntu','Cantarell','Fira Sans','Droid Sans','Helvetica Neue',sans-serif\">\n" +
                    "                                        <h2 style=\"font-weight:normal;margin:0;font-size:24px;margin-bottom:10px\">\n" +
                    "                                            Chào mừng bạn đến với dịch vụ bán hàng trực tuyến - MART-SKY\n" +
                    "                                        </h2>\n" +
                    "                                        <p style=\"margin:0;color:#777;line-height:150%;font-size:16px\">\n" +
                    "                                            \"Chúc mừng bạn đã kích hoạt tài khoản khách hàng thành công. Lần mua hàng\n" +
                    "                                            tiếp\n" +
                    "                                            theo, hãy đăng nhập để việc thanh toán thuận tiện hơn.\n" +
                    "                                        </p>\n" +
                    "                                        <table style=\"border-spacing:0;border-collapse:collapse;width:100%;margin-top:20px\">\n" +
                    "                                            <tbody>\n" +
                    "                                            <tr>\n" +
                    "                                                <td style=\"font-family:-apple-system,BlinkMacSystemFont,'Segoe UI','Roboto','Oxygen','Ubuntu','Cantarell','Fira Sans','Droid Sans','Helvetica Neue',sans-serif\">\n" +
                    "                                                    <table style=\"border-spacing:0;border-collapse:collapse;float:right;margin-right:15px\">\n" +
                    "                                                        <tbody>\n" +
                    "                                                        <tr >\n" +
                    "                                                            <td style=\"font-family:-apple-system,BlinkMacSystemFont,'Segoe UI','Roboto','Oxygen','Ubuntu','Cantarell','Fira Sans','Droid Sans','Helvetica Neue',sans-serif;text-align: center;padding: 20px 25px;border-radius: 4px;background-color: #0693e3\" onmouseover=\"this.style.cursor='pointer'\">\n" +
                    "                                                                <a href=\"" + domainClient + "\"" +
                    "                                                                 style=\"font-size:16px;text-decoration:none;color:#fff\" target=\"_blank\">Đến cửa hàng chúng tôi</a>\n" +
                    "                                                            </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                        </tbody>\n" +
                    "                                                    </table>\n" +
                    "                                                </td>\n" +
                    "                                            </tr>\n" +
                    "                                            </tbody>\n" +
                    "                                        </table>\n" +
                    "                                    </td>\n" +
                    "                                </tr>\n" +
                    "                                </tbody>\n" +
                    "                            </table>\n" +
                    "                        </td>\n" +
                    "                    </tr>\n" +
                    "                    </tbody>\n" +
                    "                </table>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "        </tbody>\n" +
                    "    </table>\n" +
                    "\n" +
                    "</div>";
//        result.append("[");
//        result.append(subject);
//        result.append("] Mail xác nhận đăng ký thàng viên T&N");
//
//        message.setSubject(result.toString());
//        result.setLength(0);
//        message.setFrom(username);
//        message.setTo(toEmail);
//        result.append("<h2>");
//        result.append(body);
//        result.append("</h2>");
            helper.setTo(toEmail);
            helper.setFrom(username);
            helper.setSubject(mailSubject);
            helper.setText(mailContent, true);


            javaMailSender.send(message);
            System.out.println("Mail sent successfuly ....");
        } catch (Exception ex) {
            log.error("Exception occurred while EmailSenderService:sendEmail send notify new account to email , Exception message {}", ex.getMessage());
        }

    }
}
