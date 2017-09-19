package cn.ajajaaj.jx.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.ajajaaj.jx.domain.Contract;
import cn.ajajaaj.jx.service.ContractService;
import cn.ajajaaj.utils.MailUtil;

public class ShipTimeJob {
	private ContractService contractService;

	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}

	public void sendMail() {
		/*
		 * String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		 * String hql = "from Contract where to_char(shipTime,'yyyy-mm-dd')=?";
		 * List<Contract> list = contractService.find(hql, Contract.class, new
		 * String[] {time});
		 */
		String hql = "from Contract where to_char(shipTime,'yyyy-mm-dd') = to_char(sysdate,'yyyy-mm-dd')";

		List<Contract> list = contractService.find(hql, Contract.class, null);
		
		System.out.println("for外");
		System.out.println(list.size());
		for (final Contract contract : list) {
			System.out.println("for");
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						MailUtil.sendMail("pokemonfan11@sina.com", "船期到啦",
								"你的合同" + contract.getContractNo() + "这批货要出货啦");
						System.out.println("进入了");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}).start();
		}
	}
}
