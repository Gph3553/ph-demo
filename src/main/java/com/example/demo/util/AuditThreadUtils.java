package com.example.demo.util;

import com.example.demo.mapper.BelongSystemMapper;
import com.example.demo.mapper.CategoryItemMapper;
import com.example.demo.model.CategoryItem;
import com.example.demo.model.RCBelongSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * <h3>dictory</h3>
 * <p>异步线程调用--审批</p>
 *
 * @author : PanhuGao
 * @date : 2020-05-25 22:32
 **/
@Component
@DependsOn("springContextUtil")
public class AuditThreadUtils {
      @Autowired
      private ThreadPoolUtil threadPoolUtil;
//   private static AuditThreadUtils auditThreadUtils;
//    @Autowired
//    private CategoryItemMapper categoryItemMapper;
//    @Autowired
//    private BelongSystemMapper belongSystemMapper;
   private   CategoryItemMapper categoryItemMapper = SpringContextUtil.getBean(CategoryItemMapper.class);
   private     BelongSystemMapper belongSystemMapper = SpringContextUtil.getBean(BelongSystemMapper.class);
//   @PostConstruct
//   public void init(){
//           auditThreadUtils = this ;
//           auditThreadUtils.categoryItemMapper = this.categoryItemMapper;
//           auditThreadUtils.belongSystemMapper = this.belongSystemMapper;
//   }


    /**
     *增加信息项
     * @throws InterruptedException
     */
    public Future addItem(  CountDownLatch downLatch ,final List<CategoryItem> categoryItemList, final Long accessId)  {
        System.out.println(System.currentTimeMillis() + ":" +threadPoolUtil.getInstance() );
        Future<Integer> submit = (Future<Integer>) threadPoolUtil.getInstance().submit(
                new Runnable() {
                    public void run() {
//                        for (CategoryItem categoryItem : categoryItemList) {
//                            categoryItem.setTenantId(1L).setCreator(3L).setDataAccessId(accessId)
//                                    .setId(IdGeneratorUtil.getId());
//                        }
                        categoryItemMapper.saveCategoryList(categoryItemList);
                        System.out.println("++++++++++++信息项++++++++++++++++++++");
                        downLatch.countDown();
                    }
                });
        return submit;
    }

    /**
     * 增加所属系统
     */

    public Future addSystem(  CountDownLatch downLatch ,final List<RCBelongSystem> belongSystemList, final Long accessId){
        System.out.println(System.currentTimeMillis() + ":" +threadPoolUtil.getInstance() );
        Future<Integer> submit = (Future<Integer>) ThreadPoolUtil.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                if (!CollectionUtils.isEmpty(belongSystemList)) {
//                    for (RCBelongSystem belongSystem : belongSystemList) {
////                        belongSystem.setId(IdGeneratorUtil.getId()).setDataAccessId(accessId).setCreator(3L);
////                    }
                    belongSystemMapper.saveBelongSystemList(belongSystemList);
                }
                System.out.println("++++++++++++所属系统++++++++++++++++++++");
                downLatch.countDown();
            }
        });
        return submit;
    }

}
