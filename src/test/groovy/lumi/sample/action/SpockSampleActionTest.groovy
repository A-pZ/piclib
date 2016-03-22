/**
 *
 */
package lumi.sample.action

import lumi.sample.service.SampleService

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

import spock.lang.Specification
import spock.lang.Unroll

import com.opensymphony.xwork2.ActionSupport

/**
 * @author A-pZ
 *
 */
class SpockSampleActionTest extends Specification {

	@InjectMocks
	SampleAction action

	@Mock
	SampleService service

	def setup() {
		MockitoAnnotations.initMocks(this)
	}

	@Unroll
	def "execute()"() {
		setup:
		Mockito.when(service.execute(Mockito.any())).thenReturn(null)

		expect:
		String result = action.execute()
		assert ( result == ActionSupport.SUCCESS)
	}
}
