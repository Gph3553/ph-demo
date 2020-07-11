//package com.example.demo.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.HttpHost;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.client.CredentialsProvider;
//import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * ES Config
// * @author zdl
// */
//
//@Slf4j
//@ConditionalOnProperty(prefix = "elasticsearch", name = "open", havingValue = "true", matchIfMissing = true)
//public class ElasticSearchConfig {
//
//    @Value("${elasticsearch.hostNameAndPort}")
//    private String hostNameAndPort;
//
//    @Value("${elasticsearch.needpassword}")
//    private Boolean needpassword;
//    @Value("${elasticsearch.username}")
//    private String username;
//    @Value("${elasticsearch.password}")
//    private String password;
//
//    public RestHighLevelClient restHighLevelClient() {
//        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(this.getClientBulider());
//        return restHighLevelClient;
//    }
//
//    public RestClientBuilder getClientBulider(){
//        String [] hostNamesPort = hostNameAndPort.split(",");
//        String host;
//        int port;
//        String[] temp;
//
//        List<HttpHost> httpHosts = new ArrayList<>();
//
//        /*restClient 初始化*/
//        for (String hostPort : hostNamesPort) {
//            temp = hostPort.split(":");
//            host = temp[0].trim();
//            port = Integer.parseInt(temp[1].trim());
//            httpHosts.add(new HttpHost(host, port, "http"));
//        }
//
//        RestClientBuilder restClientBuilder = RestClient.builder(httpHosts.toArray(new HttpHost[hostNamesPort.length])).setRequestConfigCallback(builder -> builder.setConnectTimeout(600000).setSocketTimeout(600000));
//
//        /*2.设置在同一请求进行多次尝试时应该遵守的超时时间。默认值为30秒，与默认`socket`超时相同。
//            如果自定义设置了`socket`超时，则应该相应地调整最大重试超时。*/
////        restClientBuilder.setMaxRetryTimeoutMillis(10000);
//
//        /*3.设置每次节点发生故障时收到通知的侦听器。内部嗅探到故障时被启用。*/
////        restClientBuilder.setFailureListener(new RestClient.FailureListener() {
////            @Override
////            public void onFailure(HttpHost host) {
////                // TODO 这里是当嗅探器测出某个节点有故障时，如何提醒用户，或有问题后做其他操作。
////            }
////        });
//
//        /*5.//设置修改 http 客户端配置的回调（例如：ssl 加密通讯，线程IO的配置，或其他任何         设置）*/
//
//        //简单的身份认证
//        if(needpassword){
//            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
//
//            restClientBuilder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//                @Override
//                public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
//                    httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
//                    //线程设置
////                    httpClientBuilder.setDefaultIOReactorConfig(IOReactorConfig.custom().setIoThreadCount(100).build());
//                    return httpClientBuilder;
//                }
//            });
//        }
//
//        return restClientBuilder;
//    }
//
//}
