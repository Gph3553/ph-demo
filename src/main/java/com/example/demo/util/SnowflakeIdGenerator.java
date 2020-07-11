//package com.example.demo.util;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class SnowflakeIdGenerator {
//    //private static final Logger log = LoggerFactory.getLogger(SnowflakeIdGenerator.class);
//    private final long workerId;
//    private final long datacenterId;
//    private long sequence = 0L;
//    private long lastTimestamp = -1L;
//    private static final long INIT_TIME_STAMP = 1535385600000L;
//    private static final long WORK_ID_BITS = 5L;
//    private static final long DATACENTER_ID_BITS = 5L;
//    private static final long MAX_WORKER_ID = 31L;
//    private static final long MAX_DATACENTER_ID = 31L;
//    private static final long SEQUENCE_BITS = 12L;
//    private static final long WORKERID_OFFSET = 12L;
//    private static final long DATACENTER_OFFSET = 17L;
//    private static final long TIMESTAMP_OFFSET = 22L;
//    private static final long SEQUENCE_MASK = 4095L;
//
//    public SnowflakeIdGenerator(long datacenterId, long workerId) {
//        //Assert.isTrue(workerId < 31L && workerId >= 0L, String.format("worker can't be greater than %d or less than 0", 31L));
//        //Assert.isTrue(datacenterId < 31L && datacenterId >= 0L, String.format("datacenter can't be greater than %d or less than 0", 31L));
//        //log.info("创建 Snowflake IdGenerator: datacenter={}, worker={}", datacenterId, workerId);
//        this.workerId = workerId;
//        this.datacenterId = datacenterId;
//    }
//
//    public List<Long> getIds(int count) {
//        if (count <= 1000 && count > 0) {
//            List<Long> result = new ArrayList(count);
//
//            for (int i = 0; i < count; ++i) {
//                result.add(this.getId());
//            }
//
//            return result;
//        } else {
//            throw new IllegalStateException("" + count + " 超过最大批次数量 " + 1000 + " 或 小于等于零");
//        }
//    }
//
//    public synchronized Long getId() {
//        long timestamp = System.currentTimeMillis();
//        if (timestamp < this.lastTimestamp) {
//            throw new RuntimeException("当期时间小于上一次记录的时间戳，系统时间可能发生回退");
//        } else {
//            if (this.lastTimestamp == timestamp) {
//                this.sequence = this.sequence + 1L & 4095L;
//                if (this.sequence == 0L) {
//                    timestamp = tilNextMillos(this.lastTimestamp);
//                }
//            } else {
//                this.sequence = 0L;
//            }
//
//            this.lastTimestamp = timestamp;
//            return timestamp - 1535385600000L << 22 | this.datacenterId << 17 | this.workerId << 12 | this.sequence;
//        }
//    }
//
//    private static long tilNextMillos(long lastTimestamp) {
//        long timestamp;
//        for (timestamp = System.currentTimeMillis(); timestamp <= lastTimestamp; timestamp = System.currentTimeMillis()) {
//        }
//
//        return timestamp;
//    }
//
//}
