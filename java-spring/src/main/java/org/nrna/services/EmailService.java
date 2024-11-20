package org.nrna.services;

import com.sun.mail.smtp.SMTPTransport;
import org.nrna.models.UserProfile;
import org.nrna.models.dto.User;
import org.nrna.models.dto.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {

    @Autowired
    public UserService userService;

    @Value("${aws.mail.host}")
    private String smtp_server ;

    @Value("${aws.mail.username}")
    private String smtp_username;

    @Value("${aws.mail.password}")
    private String smtp_password;

    @Value("${aws.mail.properties.mail.transport.protocol}")
    private String mail_protocol;

    @Value("${aws.mail.properties.mail.smtp.port}")
    private String mail_port;

    @Value("${aws.mail.properties.mail.smtp.auth}")
    private String mail_auth;

    @Value("${aws.mail.properties.mail.smtp.starttls.enable}")
    private String tls_enable;

    @Value("${aws.mail.properties.mail.smtp.starttls.required}")
    private String tls_required;

    @Value("${aws.from.email.address}")
    private String mail_from;

    @Value("${mail.smtp.connectiontimeout}")
    private String connection_timout;

    @Value("${mail.smtp.timeout}")
    private String smtp_timeout;

    @Value("${mail.smtp.writetimeout}")
    private String smtp_writeTimeout;

    private String getNameOrEmail(UserProfile userProfile) {
        if (userProfile != null) {
            if (userProfile.getFirstName() != null) {
                return userProfile.getFirstName();
            } else {
                return userProfile.getEmail();
            }
        }
        return null;
    }

    private UserProfile getUserProfile(){
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetailsImpl != null){
            User user = userService.getUser(userDetailsImpl.getId());
            return UserProfile.userDetailsToUserProfile(user);
        }
        return null;
    }

    public void sendEmail(UserProfile userProfile, String emailType, String token) {

        Properties props = new Properties();
        props.put("mail.transport.protocol", mail_protocol);
        props.put("mail.smtp.host", smtp_server);
        props.put("mail.smtp.port", mail_port);
        props.put("mail.smtp.starttls.enable", tls_enable);
        props.put("mail.smtp.auth", tls_required);
        /* Return path for bounces and complaints. Pick an address you own */
        props.put("mail.smtp.from", mail_from);

        props.put("mail.smtp.connectiontimeout", connection_timout);
        props.put("mail.smtp.timeout", smtp_timeout);
        props.put("mail.smtp.writetimeout", smtp_writeTimeout);

        Session session = Session.getDefaultInstance(props);
        try (SMTPTransport transport = (SMTPTransport) session.getTransport()) {
            transport.connect(smtp_username, smtp_password);

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(userProfile.getEmail()));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(userProfile.getEmail()));
            if(emailType == "password-reset"){
                msg.setSubject("Password Reset");
            }else {
                msg.setSubject("Profile Created");
            }

            msg.setContent("<h3>Hello " + getNameOrEmail(userProfile) + "</h3>" +
                    "<div class=\"preheader\" style=\"display: none; max-width: 0; max-height: 0; overflow: hidden; font-size: 1px; line-height: 1px; color: #fff; opacity: 0;\">\n" +
                    "    A preheader is the short summary text that follows the subject line when an email is viewed in the inbox.\n" +
                    "  </div>\n" +
                    "  <!-- end preheader -->\n" +
                    "\n" +
                    "  <!-- start body -->\n" +
                    "  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "\n" +
                    "    <!-- start logo -->\n" +
                    "    <tr>\n" +
                    "      <td align=\"center\" bgcolor=\"#e9ecef\">\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                    "        <tr>\n" +
                    "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                    "        <![endif]-->\n" +
                    "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                    "          <tr>\n" +
                    "            <td align=\"center\" valign=\"top\" style=\"padding: 36px 24px;\">\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "        </table>\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        </td>\n" +
                    "        </tr>\n" +
                    "        </table>\n" +
                    "        <![endif]-->\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "    <!-- end logo -->\n" +
                    "\n" +
                    "    <!-- start hero -->\n" +
                    "    <tr>\n" +
                    "      <td align=\"center\" bgcolor=\"#e9ecef\">\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                    "        <tr>\n" +
                    "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                    "        <![endif]-->\n" +
                    "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                    "          <tr>\n" +
                    "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 36px 24px 0; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; border-top: 3px solid #d4dadf;\">\n" +
                    "              <h1 style=\"margin: 0; font-size: 20px; font-weight: 700; letter-spacing: -1px; text-align: center; line-height: 48px;\">NRNA Student Network</h1>\n" +
                    "              <h1 style=\"margin: 0; font-size: 16px; font-weight: 700; letter-spacing: -1px; text-align: center; line-height: 48px;\">Reset Your Password</h1>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "        </table>\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        </td>\n" +
                    "        </tr>\n" +
                    "        </table>\n" +
                    "        <![endif]-->\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "    <!-- end hero -->\n" +
                    "\n" +
                    "    <!-- start copy block -->\n" +
                    "    <tr>\n" +
                    "      <td align=\"center\" bgcolor=\"#e9ecef\">\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                    "        <tr>\n" +
                    "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                    "        <![endif]-->\n" +
                    "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                    "\n" +
                    "          <!-- start copy -->\n" +
                    "          <tr>\n" +
                    "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\n" +
                    "              <p style=\"margin: 0;\">We received a request to reset your password for your NRNA Student Network account. If you didn't request a new password, you can safely delete this email.</p>\n" +
                    "              <p style=\"margin: 0;\">Please enter this token in the app.</p>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "          <!-- end copy -->\n" +
                    "\n" +
                    "          <!-- start button -->\n" +
                    "          <tr>\n" +
                    "            <td align=\"left\" bgcolor=\"#ffffff\">\n" +
                    "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "                <tr>\n" +
                    "                  <td align=\"center\" bgcolor=\"#ffffff\" style=\"padding: 8px; font-weight: 700\">\n" +
                                            token +
                    "                  </td>\n" +
                    "                </tr>\n" +
                    "              </table>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "          <!-- end button -->\n" +
                    "          <!-- start copy -->\n" +
                    "          <tr>\n" +
                    "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px; border-bottom: 3px solid #d4dadf\">\n" +
                    "              <p style=\"margin: 0;\">Thanks,<br> NRNA Student Network</p>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "          <!-- end copy -->\n" +
                    "\n" +
                    "        </table>\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        </td>\n" +
                    "        </tr>\n" +
                    "        </table>\n" +
                    "        <![endif]-->\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "    <!-- end copy block -->\n" +
                    "\n" +
                    "    <!-- start footer -->\n" +
                    "    <tr>\n" +
                    "      <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 24px;\">\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                    "        <tr>\n" +
                    "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                    "        <![endif]-->\n" +
                    "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                    "\n" +
                    "          <!-- start permission -->\n" +
                    "          <tr>\n" +
                    "            <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 12px 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 20px; color: #666;\">\n" +
                    "              <p style=\"margin: 0;\">You received this email because we received a request for Password Reset for your account. If you didn't request Password Reset, you can safely delete this email.</p>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "          <!-- end permission -->\n" +
                    "\n" +
                    "          <!-- start unsubscribe -->\n" +
                    "          <tr>\n" +
                    "            <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 12px 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 20px; color: #666;\">\n" +
                    "              <p style=\"margin: 0;\">NRNA Student Network</p>\n" +
                    "              <p style=\"margin: 0;\">10520 Warwick Ave, Fairfax, VA 22030</p>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "          <!-- end unsubscribe -->\n" +
                    "\n" +
                    "        </table>\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        </td>\n" +
                    "        </tr>\n" +
                    "        </table>\n" +
                    "        <![endif]-->\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "    <!-- end footer -->\n" +
                    "\n" +
                    "  </table>", "text/html; charset=utf-8");
            msg.saveChanges();

            transport.sendMessage(msg, msg.getAllRecipients());
            /* Log your SES message Id, AWS Support might need it */
            String serverResponse = transport.getLastServerResponse();
            String prefix = "250 Ok ";
            if (serverResponse.startsWith(prefix)) {
                String messageId = serverResponse.substring(prefix.length());
                System.out.println("\nMessageId: " + messageId);
                System.out.println("Email Sent to: " + userProfile.getEmail());
            }
        } catch (SendFailedException e) {
            throw new RuntimeException(e);
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}