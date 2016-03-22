package lumi.sample.service

import lumi.dao.DAO
import lumi.sample.dto.SampleDTO

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

import spock.lang.Specification
import spock.lang.Unroll

class SampleServiceTest extends Specification {

	@InjectMocks
	SampleService service;

	@Mock
	DAO dao;

	@Mock
	SampleDTO sampleDTO

	def setup() {
		MockitoAnnotations.initMocks(this)
	}

	@Unroll
	def "executeメソッド"() {
		setup:


		expect:

		Mockito.when(sampleDTO.getMail()).thenReturn(mail);

		false
		where:

		mail || result


	}
}
