/*
 * EDS Card block.
 * Authors write a 1-row, 2-cell table; EDS delivers it as nested <div>s:
 *
 *   <div class="card block">
 *     <div>                          <- row
 *       <div><picture>..</picture></div>     <- cell 0: image
 *       <div><h3>..</h3><p>..</p><p><a>..</a></p></div>  <- cell 1: text
 *     </div>
 *   </div>
 *
 * decorate() transforms that generic DOM into a semantic card.
 */
export default function decorate(block) {
  const row = block.firstElementChild;
  if (!row) return;

  const [imageCell, textCell] = row.children;

  // Label the cells so CSS can style them.
  if (imageCell) imageCell.classList.add('card-image');
  if (textCell) textCell.classList.add('card-content');

  // Promote the link's <p> wrapper into a styled CTA button.
  const link = textCell && textCell.querySelector('a');
  if (link) {
    link.classList.add('card-cta');
    const wrapper = link.closest('p');
    if (wrapper) wrapper.replaceWith(link);
  }

  // Flatten: drop the wrapping row, keep the two labelled cells.
  block.textContent = '';
  if (imageCell) block.append(imageCell);
  if (textCell) block.append(textCell);
}
