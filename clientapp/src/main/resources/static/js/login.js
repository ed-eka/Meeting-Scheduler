function typeWriter(element) {
    const textArray = element.innerHTML.split('');
    element.innerHTML = '';
    textArray.forEach((letter, i) =>
        setTimeout(() => (element.innerHTML += letter), 100 * i)
    );
}

typeWriter(typewriter);