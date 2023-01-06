package adeo.leroymerlin.cdp;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class EventServiceTest {
    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Test
    public void should_return_empty_result() {
        Mockito.when(eventRepository.findAllBy()).thenReturn(Collections.emptyList());
        List<Event> result = eventService.getFilteredEvents("query");
        assert result.isEmpty();
    }

    @Test
    public void should_return_correct_output() {
        Set<Band> bands = new HashSet();
        Band band1 = new Band();
        band1.setName("Band one");

        Set<Member> memberList = new HashSet<>();
        Member member1 = new Member();
        member1.setName("Member 1");
        Member member2 = new Member();
        member2.setName("Member 2");
        memberList.add(member1);
        memberList.add(member2);
        band1.setMembers(memberList);
        bands.add(band1);

        Event event = new Event();
        event.setId(1L);
        event.setBands(bands);
        event.setTitle("Event one");
        event.setImgUrl("https://monimage.com?id=12ODJK");
        event.setNbStars(4);

        List<Event> events = new ArrayList<>();
        events.add(event);

        Mockito.when(eventRepository.findAllBy()).thenReturn(events);

        Set<Band> bandsExp = new HashSet();
        Band band1Exp = new Band();
        band1Exp.setName("Band one [2]");

        Set<Member> memberListExp = new HashSet<>();
        Member member1Exp = new Member();
        member1Exp.setName("Member 1");
        Member member2Exp = new Member();
        member2Exp.setName("Member 2");
        memberListExp.add(member1);
        memberListExp.add(member2);

        band1Exp.setMembers(memberListExp);
        bandsExp.add(band1Exp);

        Event eventExp = new Event();
        eventExp.setBands(bandsExp);
        eventExp.setTitle("Event one [1]");
        eventExp.setImgUrl("https://monimage.com?id=12ODJK");
        eventExp.setNbStars(4);

        List<Event> expectedExp = new ArrayList<>();
        expectedExp.add(event);

        List<Event> actual = eventService.getFilteredEvents("e");
        Assert.assertEquals(actual, expectedExp);
    }
}
