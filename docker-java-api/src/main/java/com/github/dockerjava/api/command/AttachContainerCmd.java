package com.github.dockerjava.api.command;

import java.io.InputStream;

import javax.annotation.Nonnull;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Frame;

/**
 * Attach to container
 *
 * @param logs
 *            - true or false, includes logs. Defaults to false.
 *
 * @param followStream
 *            - true or false, return stream. Defaults to false.
 * @param stdout
 *            - true or false, includes stdout log. Defaults to false.
 * @param stderr
 *            - true or false, includes stderr log. Defaults to false.
 * @param timestamps
 *            - true or false, if true, print timestamps for every log line. Defaults to false.
 */
@DockerCommand
public interface AttachContainerCmd extends AttachContainer, AsyncDockerCmd<AttachContainerCmd, Frame> {

    default AttachContainerCmd fromSpec(AttachContainerSpec spec) {
        return this
                .withFollowStream(spec.hasFollowStreamEnabled())
                .withStdErr(spec.hasStderrEnabled())
                .withStdOut(spec.hasStdoutEnabled())
                .withLogs(spec.hasLogsEnabled())
                .withStdIn(spec.getStdin())
                .withContainerId(spec.getContainerId())
                .withTimestamps(spec.hasTimestampsEnabled());
    }

    AttachContainerCmd withContainerId(@Nonnull String containerId);

    /**
     * Following the stream means the resulting {@link InputStream} returned by {@link #exec()} reads infinitely. So a
     * {@link InputStream#read()} MAY BLOCK FOREVER as long as no data is streamed from the docker host to {@link DockerClient}!
     */
    AttachContainerCmd withFollowStream(Boolean followStream);

    AttachContainerCmd withTimestamps(Boolean timestamps);

    AttachContainerCmd withStdOut(Boolean stdout);

    AttachContainerCmd withStdErr(Boolean stderr);

    AttachContainerCmd withStdIn(InputStream stdin);

    AttachContainerCmd withLogs(Boolean logs);

    interface Exec extends DockerCmdAsyncExec<AttachContainerCmd, Frame> {
    }

}