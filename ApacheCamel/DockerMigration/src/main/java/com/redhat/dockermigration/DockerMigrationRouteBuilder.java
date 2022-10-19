package com.redhat.dockermigration;

import static org.apache.camel.component.exec.ExecBinding.EXEC_COMMAND_ARGS;

import org.apache.camel.builder.RouteBuilder;

import org.springframework.stereotype.Component;

@Component
public class DockerMigrationRouteBuilder extends RouteBuilder {


	@Override
	public void configure() throws Exception {
		from("file:docker-images/?noop=true")
				.split(body().tokenize("\n"))
				.process(exchange -> {
					String myBody = exchange.getIn().getBody(String.class);
					exchange.getIn().setHeader("Image", myBody);
					exchange.getIn().setHeader(EXEC_COMMAND_ARGS,"pull docker-registry.upshift.redhat.com/fuse-qe/" + myBody);
				})
				.to("exec:docker")
				.to("direct:tag");

		from("direct:tag")
				.process(exchange -> {
					String image = exchange.getIn().getHeader("Image").toString();
					exchange.getIn().setHeader(EXEC_COMMAND_ARGS,"tag docker-registry.upshift.redhat.com/fuse-qe/" + image
							+ " quay.io/fuse_qe/" + image);
				})
				.to("exec:docker")
				.to("direct:push");

		from("direct:push")
				.process(exchange -> {
					String image = exchange.getIn().getHeader("Image").toString();
					exchange.getIn().setHeader(EXEC_COMMAND_ARGS,"push quay.io/fuse_qe/" + image);
					System.out.println(image);
				})
				.to("exec:docker");
	}
}
