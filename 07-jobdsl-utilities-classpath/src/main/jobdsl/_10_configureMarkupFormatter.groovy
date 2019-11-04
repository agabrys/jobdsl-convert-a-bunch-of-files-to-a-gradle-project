import jenkins.model.Jenkins
import org.example.builder.RawHtmlMarkupFormatterBuilder

Jenkins.get().setMarkupFormatter(new RawHtmlMarkupFormatterBuilder().build())
Jenkins.get().save()
