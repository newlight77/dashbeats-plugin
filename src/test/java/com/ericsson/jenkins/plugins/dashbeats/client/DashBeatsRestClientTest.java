/*
 * The MIT License
 *
 * Copyright 2014 Ericsson. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.ericsson.jenkins.plugins.dashbeats.client;

import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ekongto on 2014-09-08.
 */
public class DashBeatsRestClientTest {

    private String url;
    private String authToken;

    private String welcomeUrl;
    private String commonFaultCausesUrl;
    private String latestBuildsUrl;
    private String latestFailedBuildsUrl;
    private String topFailedJobsUrl;

    private DashBeatsRestClient client = new DashBeatsRestClient();

    @Before
    public void setUp() {
        url = "http://localhost:3030";
        authToken = "YOUR_AUTH_TOKEN";
        this.welcomeUrl = url + "/widgets/welcome";
        this.commonFaultCausesUrl = url + "/widgets/common_fault_causes";
        this.latestBuildsUrl = url + "/widgets/latest_builds";
        this.latestFailedBuildsUrl = url + "/widgets/latest_failed_builds";
        this.topFailedJobsUrl = url + "/widgets/top_failed_jobs";
    }


    /**
     * GIVEN a url of the DashBeats server
     * and a valid authorization token
     * and a valid welcome message
     * WHEN sending it to DashBeats server
     * THEN the server receives it successfully
     * and the return a successful code 204
     */
    @Test
    public void shouldSendWelcomeSuccessfully() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("auth_token", authToken);
        jsonObject.put("text", "Testing DashBeats Welcome widget");
        int code = client.post(welcomeUrl, jsonObject);
        Assert.assertEquals(204, code);
    }

    /**
     * GIVEN a url of the DashBeats server
     * and a wrong authorization token
     * and a valid welcome message
     * WHEN sending it to DashBeats server
     * THEN the server receives it
     * and the return an unauthorized code
     */
    @Test
    public void shouldFailSendingWelcomeGettingUnauthorizedCode() {
        DashBeatsRestClient client = new DashBeatsRestClient();

        url = "http://localhost:3030/widgets/welcome";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("auth_token", "Wrong token");
        jsonObject.put("text", "Hello JerseyRest");
        int code = client.post(url, jsonObject);
        Assert.assertEquals(401, code);
    }

    /**
     * GIVEN a bad url of the DashBeats server
     * and a valid authorization token
     * and a valid welcome message
     * WHEN sending it to DashBeats server
     * THEN the server receives it
     * and the return a bad request code
     */
    @Test
    public void shouldFailSendingWelcomeWithBadUrl() {
        DashBeatsRestClient client = new DashBeatsRestClient();

        url = "http://localhost:3333/widgets/welcome";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("auth_token", "YOUR_AUTH_TOKEN");
        jsonObject.put("text", "Hello JerseyRest");
        int code = client.post(url, jsonObject);
        Assert.assertEquals(400, code);
    }

    /**
     * GIVEN a url of the DashBeats server
     * and a valid authorization token
     * and a valid 'common fault causes' content
     * WHEN sending it to DashBeats server
     * THEN the server receives it successfully
     * and the return a successful code
     */
    @Test
    public void testSendCommonFaultCauses() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("auth_token", authToken);

        JSONObject jsonContent = new JSONObject();
        List<JSONObject> list = new ArrayList<JSONObject>();
        JSONObject o1 = new JSONObject();
        o1.put("label", "Missing instrument");
        o1.put("value", "0%");
        JSONObject o2 = new JSONObject();
        o2.put("label", "Horribly wrong beats");
        o2.put("value", "20%");
        list.add(o1);
        list.add(o2);
        jsonContent.put("items", list);
        jsonObject.put("label", jsonObject);

        int code = client.post(commonFaultCausesUrl, jsonObject);
        Assert.assertEquals(204, code);
    }

    /**
     * GIVEN a url of the DashBeats server
     * and a valid authorization token
     * and a valid 'latest builds' content
     * WHEN sending it to DashBeats server
     * THEN the server receives it successfully
     * and the return a successful code
     */
    @Test
    public void testSendLatestBuilds() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("auth_token", authToken);

        JSONObject jsonContent = new JSONObject();
        List<JSONObject> list = new ArrayList<JSONObject>();
        JSONObject o1 = new JSONObject();
        o1.put("label", "Violinist");
        o1.put("value", "SUCCESS");
        JSONObject o2 = new JSONObject();
        o2.put("label", "Double bass");
        o2.put("value", "SUCCESS");
        list.add(o1);
        list.add(o2);
        jsonContent.put("items", list);
        jsonObject.put("label", jsonObject);

        int code = client.post(latestBuildsUrl, jsonObject);
        Assert.assertEquals(204, code);
    }

    /**
     * GIVEN a url of the DashBeats server
     * and a valid authorization token
     * and a valid 'latest failed builds' content
     * WHEN sending it to DashBeats server
     * THEN the server receives it successfully
     * and the return a successful code
     */
    @Test
    public void testSendLatestFailedBuilds() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("auth_token", authToken);

        JSONObject jsonContent = new JSONObject();
        List<JSONObject> list = new ArrayList<JSONObject>();
        JSONObject o1 = new JSONObject();
        o1.put("label", "Singer");
        o1.put("value", "Too mad");
        JSONObject o2 = new JSONObject();
        o2.put("label", "Guitarist");
        o2.put("value", "Gone wild");
        list.add(o1);
        list.add(o2);
        jsonContent.put("items", list);
        jsonObject.put("label", jsonObject);

        int code = client.post(latestFailedBuildsUrl, jsonObject);
        Assert.assertEquals(204, code);
    }

    /**
     * GIVEN a url of the DashBeats server
     * and a valid authorization token
     * and a valid 'top failed jobs' content
     * WHEN sending it to DashBeats server
     * THEN the server receives it successfully
     * and the return a successful code
     */
    @Test
    public void testSendTopFailedJobs() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("auth_token", authToken);

        JSONObject jsonContent = new JSONObject();
        List<JSONObject> list = new ArrayList<JSONObject>();
        JSONObject o1 = new JSONObject();
        o1.put("label", "Picasso");
        o1.put("value", "FAILED");
        JSONObject o2 = new JSONObject();
        o2.put("label", "Pianist");
        o2.put("value", "FAILED");
        JSONObject o3 = new JSONObject();
        o3.put("label", "Drummer");
        o3.put("value", "FAILED");
        list.add(o1);
        list.add(o2);
        list.add(o3);
        jsonContent.put("items", list);
        jsonObject.put("label", jsonObject);

        int code = client.post(topFailedJobsUrl, jsonObject);
        Assert.assertEquals(204, code);
    }
    /**
     * GIVEN a url of the DashBeats server
     * WHEN doing a Http Get
     * THEN the server respond successfully
     * and the return code is OK
     */
    @Test
    public void shouldPingSuccessfully() {
        DashBeatsRestClient client = new DashBeatsRestClient();
        int code = client.ping(url);
        Assert.assertEquals(200, code);
    }

    /**
     * GIVEN a wrong url of the DashBeats server
     * WHEN doing a Http Get
     * THEN the server does not respond
     * and the return code is Bad Request
     */
    @Test
    public void shouldFailPinging() {
        url = "http://localhost:3000";

        DashBeatsRestClient client = new DashBeatsRestClient();
        int code = client.ping(url);
        Assert.assertEquals(400, code);
    }

}
