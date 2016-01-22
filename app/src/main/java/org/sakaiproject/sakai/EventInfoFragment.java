package org.sakaiproject.sakai;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.sakaiproject.general.Actions;
import org.sakaiproject.api.user.User;
import org.sakaiproject.api.user.UserEvents;
import org.sakaiproject.api.user.profile.Profile;


public class EventInfoFragment extends DialogFragment {

    private UserEvents selectedEvent;
    private TextView title, date, time, description, attachment, frequency, eventType, owner, site, fromSite, location;
    private Button editEvent, removeEvent;
    private LinearLayout attachmentLinear;

    public EventInfoFragment() {
    }

    /**
     * get the selected event from activity
     *
     * @param selectedEvent the user's events
     * @return the fragment with the data
     */
    public EventInfoFragment setSelectedEvent(UserEvents selectedEvent) {
        EventInfoFragment info = new EventInfoFragment();
        Bundle b = new Bundle();
        b.putSerializable("event", selectedEvent);
        info.setArguments(b);
        return info;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_event_info, container, false);

        selectedEvent = (UserEvents) getArguments().getSerializable("event");

//        //disable the title bar
//        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        findViewsById(v);

        // Inflate the layout for this fragment
        return v;
    }


    public void findViewsById(View v) {
        title = (TextView) v.findViewById(R.id.event_title);
        title.setText(selectedEvent.getTitle());

        date = (TextView) v.findViewById(R.id.event_date);
        date.setText(selectedEvent.getEventDate());

        time = (TextView) v.findViewById(R.id.event_time);
        time.setText(selectedEvent.getTimeDuration());

        description = (TextView) v.findViewById(R.id.event_description);
        description.setText(selectedEvent.getDescription());

        if (selectedEvent.getAttachments().size() > 0) {
            attachment = (TextView) v.findViewById(R.id.attachments_txt);
            attachment.setVisibility(View.VISIBLE);
            attachmentLinear = (LinearLayout) v.findViewById(R.id.attachments_linear);
            attachmentLinear.setVisibility(View.VISIBLE);

            // add attachments to the attachment linear layout on the fly
            for (int i = 0; i < selectedEvent.getAttachmentNames().size(); i++) {
                View currentAttachment = attachmentLinear.inflate(getActivity(), R.layout.attachment_row, null);

                TextView name = (TextView) currentAttachment.findViewById(R.id.attachment_name);
                name.setText(selectedEvent.getAttachmentNames().get(i));

                ImageView image = (ImageView) currentAttachment.findViewById(R.id.attachment_type_image);

                image.setImageBitmap(Actions.getAttachmentTypeImage(getContext(), selectedEvent.getAttachmentNames().get(i)));

                attachmentLinear.addView(currentAttachment);
            }

        }

        frequency = (TextView) v.findViewById(R.id.event_frequency);
        String frequencyText = getContext().getResources().getString(R.string.activity_occurs_once);
        if (selectedEvent.getRecurrenceRule() != null) {
            frequencyText = getContext().getResources().getString(R.string.every) + " " + selectedEvent.getRecurrenceRule().getInterval() + " " + selectedEvent.getRecurrenceRule().getFrequencyDescription();
            if (selectedEvent.getRecurrenceRule().getUntil() != null) {
                frequencyText += getContext().getResources().getString(R.string.ends_on) + " " + selectedEvent.getRecurrenceRule().getEndDate();
            }
        }
        frequency.setText(frequencyText);

        eventType = (TextView) v.findViewById(R.id.event_type);
        eventType.setText(selectedEvent.getType());

        site = (TextView) v.findViewById(R.id.event_site);
        site.setText(selectedEvent.getSiteName());

        if (selectedEvent.getLocation() != null && !selectedEvent.getLocation().equals("")) {
            location = (TextView) v.findViewById(R.id.event_location);
            TextView eventLocationTxt = (TextView) v.findViewById(R.id.event_location_txt);
            eventLocationTxt.setVisibility(View.VISIBLE);
            location.setVisibility(View.VISIBLE);
            location.setText(selectedEvent.getLocation());
        }

        owner = (TextView) v.findViewById(R.id.event_owner);
        fromSite = (TextView) v.findViewById(R.id.event_from_site);
        // if the owner of the event is the user show his data
        if (User.getUserId().equals(selectedEvent.getCreator())) {

            owner.setText(Profile.getDisplayName());

            fromSite.setText("\"");
            fromSite.append(Profile.getDisplayName());
            fromSite.append("\'s ");
            fromSite.append(getContext().getResources().getString(R.string.site));
            fromSite.append("\" (~");
            fromSite.append(User.getUserId());
            fromSite.append(")");

            editEvent = (Button) v.findViewById(R.id.edit_event);
            editEvent.setVisibility(View.VISIBLE);

            removeEvent = (Button) v.findViewById(R.id.remove_event);
            removeEvent.setVisibility(View.VISIBLE);
        } else {
            owner.setText(selectedEvent.getCreatorUserId());
            fromSite.setText("\"");
            fromSite.append(selectedEvent.getSiteName());
            fromSite.append("\" (");
            fromSite.append(selectedEvent.getSiteId());
            fromSite.append(")");
        }
    }

}
