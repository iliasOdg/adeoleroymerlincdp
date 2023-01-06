package adeo.leroymerlin.cdp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final String STR_FORMAT = "[VALUE]";

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAllBy();
    }

    public void delete(Long id) {
        eventRepository.delete(id);
    }

    public List<Event> getFilteredEvents(String query) {
        List<Event> events = eventRepository.findAllBy();
        // Filter the events list in pure JAVA here
        return
                events.stream()
                        .filter(event -> event.getBands().stream()
                                .anyMatch(band -> band.getMembers().stream()
                                        .anyMatch(member -> member.getName().contains(query))
                                )
                        )
                        .map(this::customFormat)
                        .collect(Collectors.toList());
    }

    /**
     * Count band size in every event et update event title
     * Count members in every band and update band name with name
     *
     * @param event event to map
     * @return Event mapped event
     */
    public Event customFormat(Event event) {
        String converted = String.join(" ", event.getTitle(), STR_FORMAT.replace("VALUE", String.valueOf(event.getBands().size())));
        event.setTitle(converted);
        Set<Band> bands = event.getBands().stream().peek(band -> {
            String convertedBrand = String.join(" ", band.getName(), STR_FORMAT.replace("VALUE", String.valueOf(band.getMembers().size())));
            band.setName(convertedBrand);
        }).collect(Collectors.toSet());
        event.setBands(bands);
        return event;
    }

    public void save(Event event) {
        eventRepository.save(event);
    }
}
