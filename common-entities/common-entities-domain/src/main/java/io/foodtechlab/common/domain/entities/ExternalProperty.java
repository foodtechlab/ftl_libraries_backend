package io.foodtechlab.common.domain.entities;

import java.util.List;

public interface ExternalProperty {
    List<ExternalLink> getExternalLinks();

    void setExternalLinks(List<ExternalLink> externalLinks);
}
