/**
 * Interface provider responsible for allowing the centralization of 'descriptions/messages' used in the application in one place.
 *
 * @author Manoel Veríssimo
 */
export interface InternationalizationResource {

  /**
   * Returns the 'description' according to the 'key' entered.
   *
   * @param key
   * @returns
   */
  getDescription(key: string): string;
}

/**
 * 'Provider' interface responsible for providing instances of 'InternationalizationResource'.
 *
 * @author Manoel Veríssimo
 */
export interface InternacionalizacaoResourceProvider {

  /**
   * InternationalizationResource instance factory.
   */
  new(): InternationalizationResource;
}

/**
 * Provider class responsible for providing instances of 'InternationalizationResource'.
 *
 * @author Manoel Veríssimo
 */
export class InternacionalizacaoResourceProvider implements InternacionalizacaoResourceProvider { }