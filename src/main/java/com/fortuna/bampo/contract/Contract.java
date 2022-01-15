package com.fortuna.bampo.contract;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * 合约包装类
 *
 * @author C__V
 * @since 0.2.0
 */
@Service
@SuppressWarnings({"unchecked", "deprecated"})
public class Contract {
    static public Web3j web3;
    static public Credentials credentials;
    static public TransactionManager transactionManager;
    static public BigOcean t2;

    @Value("${bampo.contract-address}")
    static private String contractAddress;

    static {
        web3 = Web3j.build(new HttpService("http://shanxun.cangcz.fun:8080"));
        try {
            credentials = WalletUtils.loadCredentials("123456", "credential");
        } catch (IOException | CipherException e) {
            e.printStackTrace();
        }
        transactionManager = new RawTransactionManager(web3, credentials, 666);
        BigInteger gasPrice = new BigInteger("100");
        BigInteger gasLimit = new BigInteger("1000000000");
        t2 = BigOcean.load(contractAddress, web3,
                transactionManager, gasPrice, gasLimit);
    }

    /**
     * 创建用户，在区块链里创建一个余额为0的用户
     *
     * @param uid 用户ID
     * @throws Exception 网络异常/创建用户重复
     */
    static public void createUser(String uid) throws Exception {
        t2.createUser(uid).send();
    }

    /**
     * 获得全部用户ID列表（包括已逻辑删除的用户）
     *
     * @return 用户ID列表
     * @throws Exception 网络异常
     */
    static public List<String> getUserIdList() throws Exception {
        return (List<String>) t2.getUserIdList().send();
    }

    /**
     * 逻辑删除用户
     *
     * @param uid 用户ID
     * @throws Exception 网络异常/删除用户不存在
     */
    static public void deleteUser(String uid) throws Exception {
        t2.deleteUser(uid).send();
    }

    /**
     * 系统支付给用户钱
     *
     * @param uid   收款用户ID
     * @param value 转账额
     * @param tid   交易ID
     * @throws Exception 网络异常/支付用户不存在
     */
    static public void payUser(String uid, Integer value, String tid) throws Exception {
        t2.payUser(uid, new BigInteger(value.toString()), tid).send();
    }

    /**
     * 获得用户余额
     *
     * @param uid 用户ID
     * @return 用户余额
     * @throws Exception 网络异常/查询用户不存在
     */
    static public Long getUserBalance(String uid) throws Exception {
        return Long.parseLong(t2.getUserBalance(uid).send().toString());
    }

    /**
     * 系统扣除用户钱，如果用户钱不够，则扣到0
     *
     * @param uid   被扣款用户ID
     * @param value 扣款额
     * @param tid   交易ID
     * @throws Exception 网络异常/扣款用户不存在
     */
    static public void fineUser(String uid, Integer value, String tid) throws Exception {
        t2.fineUser(uid, new BigInteger(value.toString()), tid).send();
    }

    /**
     * 用户对用户转账
     *
     * @param from  付款用户ID
     * @param to    收款用户ID
     * @param value 转账额
     * @param tid   交易ID
     * @throws Exception 网络异常/用户不存在/用户余额不够
     */
    static public void userToUserTransfer(String from, String to, Integer value, String tid) throws Exception {
        t2.usertouserTransfer(from, to, new BigInteger(value.toString()), tid).send();
    }

    /**
     * 活动支付给用户（志愿活动结束，发款时的函数），主要由智能合约内部调用
     *
     * @param aid   付款活动ID
     * @param uid   收款用户ID
     * @param value 转账额
     * @param tid   交易ID
     * @throws Exception 网络异常/活动不存在/用户不存在
     */
    static public void activityPayUser(String aid, String uid, Integer value, String tid) throws Exception {
        t2.activityPayUser(aid, uid, new BigInteger(value.toString()), tid).send();
    }

    /**
     * 用户支付给活动（活动发起和修改时，对发起者扣除抵押金），主要由智能合约内部调用
     *
     * @param uid   付款用户ID
     * @param aid   收款活动ID
     * @param value 转账额
     * @param tid   交易ID
     * @throws Exception 网络异常/活动不存在/用户不存在/用户余额不够
     */
    static public void userPayActivity(String uid, String aid, Integer value, String tid) throws Exception {
        t2.userPayActivity(uid, aid, new BigInteger(value.toString()), tid).send();
    }

    /**
     * 创建活动，创建时系统会根据报酬列表对发起人进行扣款，作为抵押金）
     *
     * @param aid             活动ID
     * @param funder          发起人用户ID
     * @param num             参与者数量（用于校验）
     * @param participantList 参与者用户ID列表
     * @param payoffList      报酬列表
     * @param tid             交易ID
     * @throws Exception 网络异常/发起人用户不存在/发起人余额不足/ 参与者数量与参与者用户ID列表长度不一致/ 参与者数量与报酬列表长度不一致/
     */
    static public void createActivity(String aid, String funder, Integer num, List<String> participantList,
                                      List<Integer> payoffList, String tid) throws Exception {
        List<BigInteger> temp = Arrays.asList(new BigInteger[10]);
        for (int i = 0; i < payoffList.size(); ++i) {
            temp.set(i, new BigInteger(payoffList.get(i).toString()));
        }
        t2.createActiviy(aid, funder, new BigInteger(num.toString()), participantList, temp, tid).send();
    }

    /**
     * 取消活动，并退还押金给发起人
     *
     * @param aid 活动ID
     * @param tid 交易ID
     * @throws Exception 网络异常/活动不存在/活动已结束/活动发起人不存在
     */
    static public void cancelActivity(String aid, String tid) throws Exception {
        t2.cancelActiviy(aid, tid).send();
    }

    /**
     * 更新活动信息，退还之前的抵押金， 重修计算后，再次扣除抵押金
     *
     * @param aid             活动ID
     * @param num             参与者数量（用于校验）
     * @param participantList 参与者用户ID列表
     * @param payoffList      报酬列表
     * @param refundTid       退款交易ID
     * @param fundTid         扣款交易ID
     * @throws Exception 网络异常/活动不存在/发起人余额不足/ 参与者数量与参与者用户ID列表长度不一致/ 参与者数量与报酬列表长度不一致/
     */
    static public void updateActivity(String aid, Integer num, List<String> participantList,
                                      List<Integer> payoffList, String refundTid, String fundTid) throws Exception {
        List<BigInteger> temp = Arrays.asList(new BigInteger[10]);
        for (int i = 0; i < payoffList.size(); ++i) {
            temp.set(i, new BigInteger(payoffList.get(i).toString()));
        }
        t2.updateActivity(aid, new BigInteger(num.toString()), participantList, temp, refundTid,
                fundTid).send();
    }

    /**
     * 活动结束，根据报酬列表，付款给所有参与者
     *
     * @param aid     活动ID
     * @param tidList 交易ID列表
     * @throws Exception 网络异常/活动不存在/用户不存在
     */
    static public void finishActivity(String aid, List<String> tidList) throws Exception {
        t2.finishActiviy(aid, tidList).send();
    }

    /**
     * 获取交易
     *
     * @param tid 交易ID
     * @return 5元素元组（付款用户ID, 收款用户ID， 转账额， 活动ID， 形式flag）
     * @throws Exception 网络异常/交易不存在
     */
    static public Tuple5<String, String, Long, String, Boolean> getTransaction(String tid) throws Exception {
        Tuple5<String, String, BigInteger, String, Boolean> temp = t2.getTransaction(tid).send();
        return new Tuple5<>(
                temp.component1(),
                temp.component2(),
                Long.parseLong(temp.component3().toString()),
                temp.component4(),
                temp.component5());
    }

    /**
     * 测试交易是否存在
     *
     * @param tid 交易ID
     * @return Boolean 是否存在
     * @throws Exception 网络异常
     */
    static public Boolean transactionTest(String tid) throws Exception {
        return t2.transactionTest(tid).send();
    }

    /**
     * 获得用户，包括逻辑删除的用户
     *
     * @param uid 用户ID
     * @return 4元素元组（用户余额， 用户参与过的活动ID列表， 用户相关交易ID列表， 形式flag）
     * @throws Exception 网络异常
     */
    static public Tuple4<Long, List<String>, List<String>, Boolean> getUser(String uid) throws Exception {
        Tuple4<BigInteger, List<String>, List<String>, Boolean> temp = t2.getUser(uid).send();
        return new Tuple4<>(
                Long.parseLong(temp.component1().toString()),
                temp.component2(),
                temp.component3(),
                temp.component4());
    }

    /**
     * 测试用户是否存在
     *
     * @param uid 用户ID
     * @return Boolean 是否存在
     * @throws Exception 网络异常
     */
    static public Boolean userTest(String uid) throws Exception {
        return t2.userTest(uid).send();
    }

    /**
     * 获得活动
     *
     * @param aid 活动ID
     * @return 7元素元素（发起人ID， 参与者数量， 参与者ID列表， 报酬列表， 活动相关交易ID列表， 形式flag）
     * @throws Exception 网络异常
     */
    static public Tuple7<String, Integer, List<String>, List<BigInteger>, List<String>, Boolean,
            Boolean> getActivity(String aid) throws Exception {
        Tuple7<String, BigInteger, List<String>, List<BigInteger>, List<String>, Boolean,
                Boolean> temp = t2.getActivity(aid).send();
        return new Tuple7<>(
                temp.component1(),
                Integer.parseInt(temp.component2().toString()),
                temp.component3(),
                temp.component4(),
                temp.component5(),
                temp.component6(),
                temp.component7());
    }

    /**
     * 测试活动是否存在
     *
     * @param aid 活动ID
     * @return Boolean 是否存在
     */
    static public Boolean activityTest(String aid) {
        try {
            return t2.activityTest(aid).send();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获得活动参与者数量（用来判断活动结束时生成多少交易ID
     *
     * @param aid 活动ID
     * @return 参与活动的人数
     * @throws Exception 网络异常/活动不存在
     */
    static public long getActivityParticipantNum(String aid) throws Exception {
        return Long.parseLong(t2.getActivityParticipantNum(aid).send().toString());
    }

    /**
     * 获得全部活动ID列表
     *
     * @return 活动ID列表
     * @throws Exception 网络异常
     */
    static public List<String> getActivityIdList() throws Exception {
        return (List<String>) t2.getActivityIdList().send();
    }

    /**
     * 获得用户数量
     *
     * @return 用户数量
     * @throws Exception 网络异常
     */
    static public long getUserNum() throws Exception {
        return Long.parseLong(t2.getUserNum().send().toString());
    }

    /**
     * 分页获取用户ID列表
     *
     * @param pageNum    页码
     * @param pageLength 页长度（包含的数据量）
     * @return 分页的用户ID列表
     * @throws Exception 网络异常/获取到的页面内容为空
     */
    static public List<String> getUserIdPage(Integer pageNum, Integer pageLength) throws Exception {
        return (List<String>) t2.getUserIdPage(new BigInteger(pageNum.toString()),
                new BigInteger(pageLength.toString())).send();
    }

    /**
     * 分页获取用户参与的活动ID列表
     *
     * @param uid        用户ID
     * @param pageNum    页码
     * @param pageLength 页长度（包含的数据量）
     * @return 分页的活动ID列表
     * @throws Exception 网络异常/用户不存在/获取到的页面内容为空
     */
    static public List<String> getUserActivityPage(String uid, Integer pageNum, Integer pageLength) throws Exception {
        return (List<String>) t2.getUserActivityPage(uid, new BigInteger(pageNum.toString()),
                new BigInteger(pageLength.toString())).send();
    }

    /**
     * 分页获取用户参与的交易ID列表
     *
     * @param uid        用户ID
     * @param pageNum    页码
     * @param pageLength 页长度（包含的数据量）
     * @return 分页的交易ID列表
     * @throws Exception 网络异常/用户不存在/获取到的页面内容为空
     */
    static public List<String> getUserTransactionPage(String uid, Integer pageNum, Integer pageLength)
            throws Exception {
        return (List<String>) t2.getUserTransactionPage(uid, new BigInteger(pageNum.toString()),
                new BigInteger(pageLength.toString())).send();
    }

    /**
     * 获得活动数量
     *
     * @return 活动的数量
     * @throws Exception 网络异常
     */
    static public long getActivityNum() throws Exception {
        return Long.parseLong(t2.getActivityNum().send().toString());
    }

    /**
     * 分页获取活动相关的交易ID列表
     *
     * @param aid        活动ID
     * @param pageNum    页码
     * @param pageLength 页长度（包含的数据量）
     * @return 分页的交易ID列表
     * @throws Exception 网络异常/活动不存在/获取到的页面内容为空
     */
    static public List<String> getActivityTransactionPage(String aid, Integer pageNum, Integer pageLength)
            throws Exception {
        return (List<String>) t2.getActivityTransactionPage(aid, new BigInteger(pageNum.toString()),
                new BigInteger(pageLength.toString())).send();
    }

    /**
     * 分页获取活动参与者用户ID列表
     *
     * @param aid        活动ID
     * @param pageNum    页码
     * @param pageLength 页长度（包含的数据量）
     * @return 分页的参与者用户ID列表
     * @throws Exception 网络异常/活动不存在/获取到的页面内容为空
     */
    static public List<String> getActivityParticipantPage(String aid, Integer pageNum, Integer pageLength)
            throws Exception {
        return (List<String>) t2.getActivityParticipantPage(aid, new BigInteger(pageNum.toString()),
                new BigInteger(pageLength.toString())).send();
    }

    /**
     * 分页获取活动报酬列表
     *
     * @param aid        活动ID
     * @param pageNum    页码
     * @param pageLength 页长度（包含的数据量）
     * @return 分页的报酬列表
     * @throws Exception 网络异常/活动不存在/获取到的页面内容为空
     */
    static public List<String> getActivityPayoffPage(String aid, Integer pageNum, Integer pageLength)
            throws Exception {
        return (List<String>) t2.getActivityPayoffPage(aid, new BigInteger(pageNum.toString()),
                new BigInteger(pageLength.toString())).send();
    }
}
