package lumi.sample.service;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import lumi.service.LumiService;

/**
 * アプリケーション起動時に１つだけ立ち上がるServiceクラスのサンプル。
 *
 * @author A-pZ ( Serendipity 3 ./ as sundome goes by. )
 *
 */
@Scope("singleton")
@Service
@Log4j2
public class ConstanceService extends LumiService {

	/**
	 * 起動時の処理。
	 * @throws Exception
	 */
	@PostConstruct
	public void start() throws Exception {
		log.info(" - start.");

		loadAvailableContentTypes();
	}

	/**
	 * 終了時の処理。
	 * @throws Exception
	 */
	@PreDestroy
	public void destroy() throws Exception {
		log.info(" - destroy.");
	}

	@Getter
	List<String> availableContentTypes;

	void loadAvailableContentTypes() {
		availableContentTypes = Arrays.asList("image/jpeg","image/png");
	}

	/**
	 * DAOの指定。Mybatisを利用してデータベースアクセスを実行する。
	 */
	//@Autowired
	//private DAO dao;
}
