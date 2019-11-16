import { Pipe, PipeTransform } from '@angular/core';
import { MessageResource, MessageResourceProvider } from './message.resource';

/**
 * 'Pipe' class to provide 'i18n' feature.
 * 
 * @author Manoel Veríssimo
 */
@Pipe({
  name: 'i18n'
})
export class InternationalizationPipe implements PipeTransform {

  private messageResource: MessageResource;

  /**
   * Constructor class
   *
   * @param MessageResource
   */
  constructor(MessageResource: MessageResourceProvider) {
    this.messageResource = new MessageResource();
  }

  /**
   * Returns the description according to the given key.
   *
   * @param chave
   * @param params
   */
  transform(chave: string, params?: any): string {
    let description = this.messageResource.getDescription(chave);

    if (description !== undefined && params !== undefined) {

      if (typeof params === 'string') {
        description = description.replace(new RegExp('\\{0}', 'g'), params);

      } else {
        for (var index = 0; index < params.length; index++) {
          description = description.replace(new RegExp('\\{' + index + '\\}', 'g'), params[index]);
        }
      }
    }
    return description;
  }

}
